//
//  SignUpPresenter.swift
//  ios
//
//  Created by Артем Розанов on 18.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import shared

protocol SignUpPresenter {
  func interactorDidRequestVerificationCode(_ error: Error?)
  func interactorDidRequestSession(_ error: Error?)
}

final class SignUpPresenterImpl: SignUpPresenter {

  private let view: SignUpView

  init(view: SignUpView) {
    self.view = view
  }

  func interactorDidRequestVerificationCode(_ error: Error?) {
    guard let error = error else {
      view.onVerificationCodeRequestCompleted()
      return
    }
    view.onVerificationCodeRequestError(error)
  }

  func interactorDidRequestSession(_ error: Error?) {
    guard let error = error else {
      view.onSessionRequestCompleted()
      return
    }
    view.onSessionRequestError(error)
  }
}
