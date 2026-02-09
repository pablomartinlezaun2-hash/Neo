import SwiftUI

struct SignupView: View {
    @EnvironmentObject private var sessionViewModel: SessionViewModel
    @State private var name = ""
    @State private var email = ""
    @State private var password = ""

    var body: some View {
        VStack(spacing: 24) {
            Text("Crear cuenta")
                .font(.title.bold())
                .foregroundColor(AppConstants.Colors.primary)

            VStack(alignment: .leading, spacing: 16) {
                TextField("Nombre", text: $name)
                    .padding()
                    .background(Color.white)
                    .cornerRadius(12)

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
                    await sessionViewModel.signup(name: name, email: email, password: password)
                }
            }) {
                if sessionViewModel.isLoading {
                    ProgressView()
                        .frame(maxWidth: .infinity)
                } else {
                    Text("Registrarme")
                        .frame(maxWidth: .infinity)
                }
            }
            .buttonStyle(.borderedProminent)
            .tint(AppConstants.Colors.primary)

            if let error = sessionViewModel.authError {
                Text(error)
                    .foregroundColor(.red)
            }
        }
        .padding()
        .background(AppConstants.Colors.background.ignoresSafeArea())
    }
}
