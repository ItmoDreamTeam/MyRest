//
//  SignInRouter.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

protocol SignInRouter {
  func signInShouldOpenSignUpScene(_ signInView: SignInView, pass context: UIViewController)
  func signInShouldOpenVerificationCodeScene(
    _ signInView: SignInView,
    pass phone: String,
    and context: UIViewController
  )
}

final class SignInRouterImpl: SignInRouter {

  private let signUpScene: SignUpScene
  private let verificationCodeScene: VerificationCodeScene

  init(signUpScene: SignUpScene, verificationCodeScene: VerificationCodeScene) {
    self.signUpScene = signUpScene
    self.verificationCodeScene = verificationCodeScene
  }

  func signInShouldOpenSignUpScene(_ signInView: SignInView, pass context: UIViewController) {
    signUpScene.passedContext(context)
    signInView.navigationController?.pushViewController(signUpScene, animated: true)
  }

  func signInShouldOpenVerificationCodeScene(
    _ signInView: SignInView,
    pass phone: String,
    and context: UIViewController
  ) {
    verificationCodeScene.passedPhone(phone)
    verificationCodeScene.passedContext(context)
    signInView.navigationController?.pushViewController(verificationCodeScene, animated: true)
  }
}
