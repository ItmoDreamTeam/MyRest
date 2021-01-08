//
//  IOSErrorHandler.swift
//  ios
//
//  Created by Артем Розанов on 06.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

protocol IOSErrorHandlerDelegate: class {
  func handleServerError()
}

final class IOSErrorHandler: ErrorHandler<UIViewController> {

  private let router: ToSignInRouter

  init(router: ToSignInRouter) {
    self.router = router
  }

  override func handleUnauthenticatedError(context: UIViewController?) {
    guard let context = context else { return }
    router.sceneShouldOpenSignInScene(context)
  }

  override func handleServerError(context: UIViewController?, errors: [ServerError]) {
    guard let context = context as? IOSErrorHandlerDelegate else { return }
    context.handleServerError()
  }
}
