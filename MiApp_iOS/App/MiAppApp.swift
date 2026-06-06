import SwiftUI

@main
struct MiAppApp: App {
    @StateObject private var sessionViewModel = SessionViewModel()
    @StateObject private var notificationManager = NotificationManager()
    @StateObject private var locationManager = LocationManager()

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(sessionViewModel)
                .environmentObject(notificationManager)
                .environmentObject(locationManager)
                .onAppear {
                    notificationManager.configure()
                    locationManager.requestAuthorization()
                }
        }
    }
}
