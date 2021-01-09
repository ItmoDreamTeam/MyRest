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
  private let errorHandler: IOSErrorHandler
  private let presenter: SignUpPresenter

  init(userClient: UserClient, errorHandler: IOSErrorHandler, presenter: SignUpPresenter) {
    self.userClient = userClient
    self.errorHandler = errorHandler
    self.presenter = presenter
  }

  func signUpDidRequestVerificationCode(_ signUpView: SignUpView, forSignUp: SignUp) {
    userClient.signUp(signUp: forSignUp) { [weak self] result, error in
      guard result != nil else {
        self?.errorHandler.handleNSError(context: signUpView, error: error)
        return
      }
      self?.presenter.interactorDidRequestVerificationCode()
    }
  }
}
