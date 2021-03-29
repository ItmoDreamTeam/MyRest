//
//  VerificationCodePresenter.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

protocol VerificationCodePresenter {
  func interactorDidStartSession()
}

final class VerificationCodePresenterImpl: VerificationCodePresenter {

  private let view: VerificationCodeView

  init(view: VerificationCodeView) {
    self.view = view
  }

  func interactorDidStartSession() {
    view.onStartSessionCompleted()
  }
}
