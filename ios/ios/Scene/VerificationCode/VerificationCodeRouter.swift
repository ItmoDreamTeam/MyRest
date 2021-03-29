//
//  VerificationCodeRouter.swift
//  ios
//
//  Created by Артем Розанов on 10.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

protocol VerificationCodeRouter {
  func verificationCodeShouldBackTo(_ verificationCodeScene: VerificationCodeView, contex: UIViewController)
}

final class VerificationCodeRouterImpl: VerificationCodeRouter {

  func verificationCodeShouldBackTo(_ verificationCodeScene: VerificationCodeView, contex: UIViewController) {
    verificationCodeScene.navigationController?.popToViewController(contex, animated: true)
  }
}
