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
  func interactorDidRequestVerificationCode()
}

final class SignUpPresenterImpl: SignUpPresenter {

  private let view: SignUpView

  init(view: SignUpView) {
    self.view = view
  }

  func interactorDidRequestVerificationCode() {
    view.onVerificationCodeRequestCompleted()
  }
}
