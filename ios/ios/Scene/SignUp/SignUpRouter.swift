//
//  SignUpRouter.swift
//  ios
//
//  Created by Артем Розанов on 10.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

protocol SignUpRouter {
  func signUpShouldOpenVerificationCodeScene(
    _ signUpView: SignUpView,
    pass phone: String,
    and context: UIViewController
  )
}

final class SignUpRouterImpl: SignUpRouter {

  private let verificationCodeScene: VerificationCodeScene

  init(verificationCodeScene: VerificationCodeScene) {
    self.verificationCodeScene = verificationCodeScene
  }

  func signUpShouldOpenVerificationCodeScene(
    _ signUpView: SignUpView,
    pass phone: String,
    and context: UIViewController
  ) {
    verificationCodeScene.passedPhone(phone)
    verificationCodeScene.passedContext(context)
    signUpView.navigationController?.pushViewController(verificationCodeScene, animated: true)
  }
}
