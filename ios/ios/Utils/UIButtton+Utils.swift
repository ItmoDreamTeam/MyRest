//
//  UIButtton+Utils.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

extension UIButton {
  func enableButton(isEnabled: Bool) {
    self.isEnabled = isEnabled
    self.backgroundColor = isEnabled ? .gray : .lightGray
  }
}
