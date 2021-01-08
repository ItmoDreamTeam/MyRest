//
//  ToSignInRouter.swift
//  ios
//
//  Created by Артем Розанов on 08.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

protocol ToSignInRouter {
  func sceneShouldOpenSignInScene(_ scene: UIViewController)
}

final class ToSignInRouterImp: ToSignInRouter {

  private let signInScene: SignInView

  init(signInScene: SignInView) {
    self.signInScene = signInScene
  }

  func sceneShouldOpenSignInScene(_ scene: UIViewController) {
    scene.navigationController?.pushViewController(signInScene, animated: true)
  }
}
