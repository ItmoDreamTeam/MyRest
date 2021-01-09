//
//  SignUpInteractor.swift
//  ios
//
//  Created by Артем Розанов on 18.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import SwiftKeychainWrapper
import shared

protocol SignUpInteractor {
  func signUpDidRequestVerificationCode(_ signUpView: SignUpView, forSignUp: SignUp)
}

final class SignUpInteractorImpl: SignUpInteractor {

  private let userClient: UserClient
  private let keychainWrapper: KeychainWrapper
  private let presenter: SignUpPresenter

  init(userClient: UserClient, keychainWrapper: KeychainWrapper, presenter: SignUpPresenter) {
    self.userClient = userClient
    self.keychainWrapper = keychainWrapper
    self.presenter = presenter
  }

  func signUpDidRequestVerificationCode(_ signUpView: SignUpView, forSignUp: SignUp) {
    userClient.signUp(signUp: forSignUp) { [weak self] _, error in
      guard let error = error else {
        self?.presenter.interactorDidRequestVerificationCode(nil)
        return
      }
      self?.presenter.interactorDidRequestVerificationCode(error)
    }
  }
}
