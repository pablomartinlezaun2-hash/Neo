import Foundation

struct APIClient {
    let baseURL: URL
    let urlSession: URLSession

    init(baseURL: URL = AppConstants.apiBaseURL ?? URL(string: "https://api.example.com")!,
         urlSession: URLSession = .shared) {
        self.baseURL = baseURL
        self.urlSession = urlSession
    }

    func login(email: String, password: String) async throws -> AuthTokens {
        let url = baseURL.appendingPathComponent("/auth/login")
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = try JSONEncoder().encode(["email": email, "password": password])
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")

        let (data, response) = try await urlSession.data(for: request)
        try validate(response: response)
        return try JSONDecoder().decode(AuthTokens.self, from: data)
    }

    func signup(name: String, email: String, password: String) async throws -> AuthTokens {
        let url = baseURL.appendingPathComponent("/auth/signup")
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = try JSONEncoder().encode(["name": name, "email": email, "password": password])
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")

        let (data, response) = try await urlSession.data(for: request)
        try validate(response: response)
        return try JSONDecoder().decode(AuthTokens.self, from: data)
    }

    func fetchDashboard(accessToken: String) async throws -> [String] {
        let url = baseURL.appendingPathComponent("/dashboard")
        var request = URLRequest(url: url)
        request.addValue("Bearer \(accessToken)", forHTTPHeaderField: "Authorization")

        let (data, response) = try await urlSession.data(for: request)
        try validate(response: response)
        return try JSONDecoder().decode([String].self, from: data)
    }

    private func validate(response: URLResponse) throws {
        guard let httpResponse = response as? HTTPURLResponse else {
            throw URLError(.badServerResponse)
        }
        guard (200..<300).contains(httpResponse.statusCode) else {
            throw URLError(.badServerResponse)
        }
    }
}
