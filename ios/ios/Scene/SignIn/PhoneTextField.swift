//
//  PhoneTextField.swift
//  ios
//
//  Created by Артем Розанов on 03.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

final class PhoneTextField: UITextField {

  private let formattingPattern = " *** ***-**-**"
  private let replacementChar: Character = "*"
  private let prefix = "+7"

  init() {
    super.init(frame: .zero)
    self.keyboardType = .numberPad
    text = prefix
  }

  required init?(coder: NSCoder) {
    return nil
  }

  private func registerForNotifications() {
    NotificationCenter.default.addObserver(
      self,
      selector: #selector(textDidChange),
      name: NSNotification.Name(rawValue: "UITextFieldTextDidChangeNotification"),
      object: self
    )
  }

  @objc private func textDidChange() {
    guard let text = text, text.count > 2 && text.count < formattingPattern.count + 2 else { return }
    let currentCharacter = Array(formattingPattern)[text.count - 2]
    if currentCharacter == " " {
      self.text?.append(" ")
      return
    }
    if currentCharacter == "*" { return }
    if currentCharacter == "-" {
      self.text?.append("-")
      return
    }
  }

  deinit {
    NotificationCenter.default.removeObserver(self)
  }
}
