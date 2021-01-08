//
//  IOSErrorHandler.swift
//  ios
//
//  Created by Артем Розанов on 06.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

final class IOSErrorHandler: ErrorHandler<UIViewController> {

  private let router: ToSignInRouter

  init(router: ToSignInRouter) {
    self.router = router
  }

  override func handle(context: UIViewController?, exception: ClientException) {
    guard let context = context else {
      return
    }
    router.sceneShouldOpenSignInScene(context)
  }
}
