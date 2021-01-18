//
//  UIViewController+Utils.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

extension UIViewController {
  func presentToast(message: String, onTime time: DispatchTime) {
    let alert = UIAlertController(title: nil, message: message, preferredStyle: .alert)
    alert.view.backgroundColor = .black
    alert.view.alpha = 0.6
    alert.view.layer.cornerRadius = 15
    present(alert, animated: true)
    DispatchQueue.main.asyncAfter(deadline: time) {
      alert.dismiss(animated: true, completion: nil)
    }
  }
}
