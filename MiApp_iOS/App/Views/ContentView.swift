import SwiftUI

struct ContentView: View {
    @EnvironmentObject private var sessionViewModel: SessionViewModel

    var body: some View {
        NavigationStack {
            if sessionViewModel.isAuthenticated {
                HomeView()
            } else {
                LoginView()
            }
        }
        .onAppear {
            sessionViewModel.loadPersistedSession()
        }
    }
}
