//
//  SignUpPresenter.swift
//  ios
//
//  Created by Артем Розанов on 18.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation

protocol SignUpPresenter {
  func interactorDidRequestVereficationCode(_ error: Error?)
}

final class SignUpPresenterImpl: SignUpPresenter {

  private let view: SignUpView

  init(view: SignUpView) {
    self.view = view
  }

  func interactorDidRequestVereficationCode(_ error: Error?) {
    guard let error = error else  {
      view.onRequestedComplete()
      return
    }
    view.onRequestedError(error)
  }
}
