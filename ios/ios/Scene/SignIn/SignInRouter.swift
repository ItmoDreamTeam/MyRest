//
//  SignInRouter.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

protocol SignInRouter {
  func signInShouldOpenVerificationCodeScene(_ signInView: SignInView)
}

final class SignInRouterImpl: SignInRouter {

  private let verificationCodeScene: VerificationCodeView

  init(verificationCodeScene: VerificationCodeView) {
    self.verificationCodeScene = verificationCodeScene
  }

  func signInShouldOpenVerificationCodeScene(_ signInView: SignInView) {
    signInView.navigationController?.pushViewController(verificationCodeScene, animated: true)
  }
}
