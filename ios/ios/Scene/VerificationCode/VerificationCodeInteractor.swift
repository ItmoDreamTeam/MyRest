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
  func verificationCodeStartedSession(_ verificationCodeView: VerificationCodeView)
}

final class VerificationCodeInteractorImpl: VerificationCodeInteractor {

  private let errorHandler: IOSErrorHandler
  private let userClient: UserClient

  init(errorHandler: IOSErrorHandler, userClient: UserClient) {
    self.errorHandler = errorHandler
    self.userClient = userClient
  }

  func verificationCodeStartedSession(_ verificationCodeView: VerificationCodeView) {}
}
