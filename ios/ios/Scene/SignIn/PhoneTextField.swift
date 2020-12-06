//
//  PhoneTextField.swift
//  ios
//
//  Created by Артем Розанов on 03.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

final class PhoneTextField: UITextField {

  private let formattingPattern = "*** ***-**-** "
  private let replacementChar: Character = "*"
  private let prefix = " +7 "
  private var prevIdx: Int

  init() {
    prevIdx = prefix.count
    super.init(frame: .zero)
    translatesAutoresizingMaskIntoConstraints = false
    self.keyboardType = .numberPad
    text = prefix
    registerForNotifications()
  }

  required init?(coder: NSCoder) {
    return nil
  }

  override func layoutSubviews() {
    super.layoutSubviews()
    layer.borderWidth = 1
    layer.borderColor = UIColor.black.cgColor
    layer.cornerRadius = 5
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
    guard let text = text else { return }
    let textLength = text.count
    let prefixLength = prefix.count
    if prefixLength > textLength {
      self.text = prefix
      prevIdx = prefixLength
      return
    }
    if prevIdx >= textLength {
      prevIdx = textLength
      return
    }
    prevIdx = textLength
    if !(textLength < formattingPattern.count + prefixLength + 1) {
      let range = text.startIndex..<text.index(before: text.endIndex)
      self.text = String(text[range])
      return
    }
    let currentCharacter = Array(formattingPattern)[textLength - prefixLength]
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
}
