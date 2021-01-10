//
//  VerificationCodeInteractor.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

protocol VerificationCodeInteractor {
  func verificationCodeStartedSession(
    _ verificationCodeView: VerificationCodeView,
    for phone: String,
    and verificationCode: String
  )
}

final class VerificationCodeInteractorImpl: VerificationCodeInteractor {

  private let errorHandler: IOSErrorHandler
  private let userClient: UserClient
  private let accessTokenProvider: AccessTokenProviderImpl
  private let presenter: VerificationCodePresenter

  init(
    errorHandler: IOSErrorHandler,
    userClient: UserClient,
    accessTokenProvider: AccessTokenProviderImpl,
    presenter: VerificationCodePresenter
  ) {
    self.errorHandler = errorHandler
    self.userClient = userClient
    self.accessTokenProvider = accessTokenProvider
    self.presenter = presenter
  }

  func verificationCodeStartedSession(
    _ verificationCodeView: VerificationCodeView,
    for phone: String,
    and verificationCode: String
  ) {
    let signInVerification = SignInVerification(phone: phone, code: verificationCode)
    userClient.startSession(signInVerification: signInVerification) { [weak self] session, error in
      guard session != nil else {
        self?.errorHandler.handleNSError(context: verificationCodeView, error: error)
        return
      }
      self?.accessTokenProvider.accessToken = session?.token
      self?.presenter.interactorDidStartSession()
    }
  }
}
