import Foundation
import SwiftUI

@MainActor
final class SessionViewModel: ObservableObject {
    @Published var isAuthenticated = false
    @Published var user: User?
    @Published var authError: String?
    @Published var isLoading = false

    private let apiClient = APIClient()
    private let keychain = KeychainStore()
    private let tokenService = "com.norteneo.auth"

    func login(email: String, password: String) async {
        isLoading = true
        authError = nil
        do {
            let tokens = try await apiClient.login(email: email, password: password)
            try persist(tokens: tokens)
            isAuthenticated = true
            user = User(id: UUID(), name: email.components(separatedBy: "@").first ?? "Usuario", email: email)
        } catch {
            authError = "No se pudo iniciar sesión."
        }
        isLoading = false
    }

    func signup(name: String, email: String, password: String) async {
        isLoading = true
        authError = nil
        do {
            let tokens = try await apiClient.signup(name: name, email: email, password: password)
            try persist(tokens: tokens)
            isAuthenticated = true
            user = User(id: UUID(), name: name, email: email)
        } catch {
            authError = "No se pudo registrar la cuenta."
        }
        isLoading = false
    }

    func logout() {
        keychain.delete(service: tokenService, account: "access")
        keychain.delete(service: tokenService, account: "refresh")
        isAuthenticated = false
        user = nil
    }

    func loadPersistedSession() {
        guard let _ = try? keychain.read(service: tokenService, account: "access") else {
            return
        }
        isAuthenticated = true
    }

    private func persist(tokens: AuthTokens) throws {
        try keychain.save(Data(tokens.accessToken.utf8), service: tokenService, account: "access")
        try keychain.save(Data(tokens.refreshToken.utf8), service: tokenService, account: "refresh")
    }
}
