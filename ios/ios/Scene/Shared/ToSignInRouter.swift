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

  private let signInScene: SignInScene

  init(signInScene: SignInScene) {
    self.signInScene = signInScene
  }

  func sceneShouldOpenSignInScene(_ scene: UIViewController) {
    signInScene.passedContext(scene)
    guard let navigationController = scene.navigationController else {
      scene.present(signInScene, animated: true, completion: nil)
      return
    }
    navigationController.pushViewController(signInScene, animated: true)
  }
}
