import Foundation

@MainActor
final class DashboardViewModel: ObservableObject {
    @Published var cards: [String] = []
    @Published var isLoading = false
    @Published var errorMessage: String?

    private let apiClient = APIClient()

    func load(accessToken: String) async {
        isLoading = true
        errorMessage = nil
        do {
            cards = try await apiClient.fetchDashboard(accessToken: accessToken)
        } catch {
            errorMessage = "No se pudieron cargar los datos."
        }
        isLoading = false
    }
}
