import SwiftUI

enum AppConstants {
    static let apiBaseURL = URL(string: "https://api.example.com")
    static let appName = "NorteNeo"

    enum Colors {
        static let primary = Color(red: 0.11, green: 0.24, blue: 0.45)
        static let accent = Color(red: 0.98, green: 0.64, blue: 0.22)
        static let background = Color(red: 0.96, green: 0.97, blue: 0.98)
        static let textPrimary = Color(red: 0.09, green: 0.11, blue: 0.14)
        static let textSecondary = Color(red: 0.38, green: 0.42, blue: 0.48)
    }
}
