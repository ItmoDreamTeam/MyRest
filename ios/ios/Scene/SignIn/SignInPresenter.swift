//
//  SignInPresenter.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

protocol SignInPresenter {
  func interactorSendedCodeRequest()
}

final class SignInPresenterImpl: SignInPresenter {

  private let view: SignInView

  init(view: SignInView) {
    self.view = view
  }

  func interactorSendedCodeRequest() {
    view.onCodeRequestCompleted()
  }
}
