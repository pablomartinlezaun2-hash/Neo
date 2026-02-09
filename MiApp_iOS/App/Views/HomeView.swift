import SwiftUI

struct HomeView: View {
    @EnvironmentObject private var sessionViewModel: SessionViewModel
    @StateObject private var dashboardViewModel = DashboardViewModel()

    var body: some View {
        VStack(spacing: 16) {
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text("Hola, \(sessionViewModel.user?.name ?? "Usuario")")
                        .font(.title2.bold())
                    Text("Resumen diario")
                        .foregroundColor(AppConstants.Colors.textSecondary)
                }
                Spacer()
                Button("Salir") {
                    sessionViewModel.logout()
                }
                .foregroundColor(AppConstants.Colors.accent)
            }

            if dashboardViewModel.isLoading {
                ProgressView()
            } else if let error = dashboardViewModel.errorMessage {
                Text(error)
                    .foregroundColor(.red)
            } else {
                ScrollView {
                    VStack(spacing: 12) {
                        ForEach(dashboardViewModel.cards, id: \.self) { card in
                            DashboardCard(title: card)
                        }
                    }
                }
            }

            NavigationLink("Ajustes", destination: SettingsView())
                .buttonStyle(.bordered)
                .tint(AppConstants.Colors.primary)
        }
        .padding()
        .background(AppConstants.Colors.background.ignoresSafeArea())
        .task {
            guard let tokens = try? KeychainStore().read(service: "com.norteneo.auth", account: "access"),
                  let accessToken = String(data: tokens, encoding: .utf8) else {
                return
            }
            await dashboardViewModel.load(accessToken: accessToken)
        }
    }
}

private struct DashboardCard: View {
    let title: String

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(title)
                .font(.headline)
            Text("Detalle del indicador, acciones rápidas y CTA.")
                .font(.subheadline)
                .foregroundColor(AppConstants.Colors.textSecondary)
        }
        .padding()
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(Color.white)
        .cornerRadius(16)
        .shadow(color: Color.black.opacity(0.05), radius: 10, x: 0, y: 4)
    }
}
