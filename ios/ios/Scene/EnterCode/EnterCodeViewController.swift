//
//  EnterCodeViewController.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

protocol EnterCodeView: UIViewController {}

final class EnterCodeViewController: UIViewController, EnterCodeView {
  static let storyboardName = "EnterCodeViewController"
  static func storyboardInstance() -> Self? {
    let storyboard = UIStoryboard(name: storyboardName, bundle: nil)
    return storyboard.instantiateInitialViewController() as? Self
  }
}
