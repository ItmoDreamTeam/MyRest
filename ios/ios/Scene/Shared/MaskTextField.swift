//
//  PhoneTextField.swift
//  ios
//
//  Created by Артем Розанов on 03.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

final class MaskTextField: UITextField {

  private let formattingPattern: String
  private let replacementChar: Character = "*"
  private let prefix: String
  private var prevIdx: Int

  init(formattingPattern: String, prefix: String) {
    prevIdx = prefix.count
    self.formattingPattern = formattingPattern
    self.prefix = prefix
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

  func getText() -> String? {
    guard let text = self.text else { return nil }
    let filteredText = text.filter {
      return $0.isNumber ? true : false
    }
    return filteredText
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
    if textLength > formattingPattern.count + prefixLength {
      let range = text.startIndex..<text.index(before: text.endIndex)
      self.text = String(text[range])
      return
    }
    var formattedText = Array(prefix)
    for idx in 0..<textLength - prefixLength {
      let currentPattern = formattingPattern[formattingPattern.index(formattingPattern.startIndex, offsetBy: idx)]
      let currentChar = text[text.index(text.startIndex, offsetBy: idx + prefixLength)]
      if currentPattern == " " {
        formattedText.append(" ")
        if currentChar != " " {
          formattedText.append(currentChar)
        }
      }
      if currentPattern == "-" {
        formattedText.append("-")
        if currentChar != "-" {
          formattedText.append(currentChar)
        }
      }
      if currentPattern == "*" {
        formattedText.append(currentChar)
      }
    }
    self.text = String(formattedText)
  }
}
