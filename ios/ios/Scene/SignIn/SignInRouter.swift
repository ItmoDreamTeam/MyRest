//
//  SignInRouter.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

protocol SignInRouter {
  func signInShouldOpenSignUpScene(_ signInView: SignInView)
  func signInShouldOpenVerificationCodeScene(_ signInView: SignInView)
}

final class SignInRouterImpl: SignInRouter {

  private let signUpScene: SignUpView
  private let verificationCodeScene: VerificationCodeView

  init(signUpScene: SignUpView, verificationCodeScene: VerificationCodeView) {
    self.signUpScene = signUpScene
    self.verificationCodeScene = verificationCodeScene
  }

  func signInShouldOpenSignUpScene(_ signInView: SignInView) {
    signInView.navigationController?.pushViewController(signUpScene, animated: true)
  }

  func signInShouldOpenVerificationCodeScene(_ signInView: SignInView) {
    signInView.navigationController?.pushViewController(verificationCodeScene, animated: true)
  }
}
