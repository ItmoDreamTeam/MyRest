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
  func signInShouldOpenVerificationCodeScene(_ signInView: SignInView, pass phone: String)
}

final class SignInRouterImpl: SignInRouter {

  private let signUpScene: SignUpView
  private let verificationCodeScene: VerificationCodeScene

  init(signUpScene: SignUpView, verificationCodeScene: VerificationCodeScene) {
    self.signUpScene = signUpScene
    self.verificationCodeScene = verificationCodeScene
  }

  func signInShouldOpenSignUpScene(_ signInView: SignInView) {
    signInView.navigationController?.pushViewController(signUpScene, animated: true)
  }

  func signInShouldOpenVerificationCodeScene(_ signInView: SignInView, pass phone: String) {
    verificationCodeScene.signInViewPassed(phone)
    signInView.navigationController?.pushViewController(verificationCodeScene, animated: true)
  }
}
