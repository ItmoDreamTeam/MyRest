//
//  SignInInteractor.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

protocol SignInInteractor {
  func signInSceneDidRequestCode(_ signInView: SignInView, for phone: String)
}

final class SignInInteractorImpl: SignInInteractor {

  private let userClient: UserClient
  private let errorHandler: IOSErrorHandler

  init(userClient: UserClient, errorHandler: IOSErrorHandler) {
    self.userClient = userClient
    self.errorHandler = errorHandler
  }

  func signInSceneDidRequestCode(_ signInView: SignInView, for phone: String) {
    let signIn = SignIn(phone: phone)
    userClient.signIn(signIn: signIn) { [weak self] result, error in
      guard result != nil else {
        self?.errorHandler.handleNSError(context: signInView, error: error)
        return
      }
    }
  }
}
