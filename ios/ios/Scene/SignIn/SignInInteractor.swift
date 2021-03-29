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
  private let presenter: SignInPresenter

  init(userClient: UserClient, errorHandler: IOSErrorHandler, presenter: SignInPresenter) {
    self.userClient = userClient
    self.errorHandler = errorHandler
    self.presenter = presenter
  }

  func signInSceneDidRequestCode(_ signInView: SignInView, for phone: String) {
    let signIn = SignIn(phone: phone)
    userClient.signIn(signIn: signIn) { [weak self] result, error in
      guard result != nil else {
        self?.errorHandler.handleNSError(context: signInView, error: error)
        return
      }
      self?.presenter.interactorSendedCodeRequest()
    }
  }
}
