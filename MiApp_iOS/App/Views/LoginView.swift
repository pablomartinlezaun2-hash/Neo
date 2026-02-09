import SwiftUI

struct LoginView: View {
    @EnvironmentObject private var sessionViewModel: SessionViewModel
    @State private var email = ""
    @State private var password = ""

    var body: some View {
        VStack(spacing: 24) {
            Text(AppConstants.appName)
                .font(.largeTitle.bold())
                .foregroundColor(AppConstants.Colors.primary)

            VStack(alignment: .leading, spacing: 16) {
                TextField("Email", text: $email)
                    .textInputAutocapitalization(.never)
                    .keyboardType(.emailAddress)
                    .padding()
                    .background(Color.white)
                    .cornerRadius(12)

                SecureField("Contraseña", text: $password)
                    .padding()
                    .background(Color.white)
                    .cornerRadius(12)
            }

            Button(action: {
                Task {
                    await sessionViewModel.login(email: email, password: password)
                }
            }) {
                if sessionViewModel.isLoading {
                    ProgressView()
                        .frame(maxWidth: .infinity)
                } else {
                    Text("Iniciar sesión")
                        .frame(maxWidth: .infinity)
                }
            }
            .buttonStyle(.borderedProminent)
            .tint(AppConstants.Colors.primary)

            NavigationLink("Crear cuenta", destination: SignupView())
                .foregroundColor(AppConstants.Colors.accent)

            if let error = sessionViewModel.authError {
                Text(error)
                    .foregroundColor(.red)
            }
        }
        .padding()
        .background(AppConstants.Colors.background.ignoresSafeArea())
    }
}
