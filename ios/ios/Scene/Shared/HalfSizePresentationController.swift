//
//  HalfSizePresentationController.swift
//  ios
//
//  Created by Артем Розанов on 17.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

final class HalfSizePresentationController: UIPresentationController {
  override var frameOfPresentedViewInContainerView: CGRect {
    guard let containerView = containerView else { return CGRect() }
    return CGRect(
      x: 0,
      y: containerView.bounds.height / 2,
      width: containerView.bounds.width,
      height: containerView.bounds.height / 2
    )
  }
}
