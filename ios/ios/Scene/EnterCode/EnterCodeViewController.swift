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

  @IBOutlet weak var codeTextField: MaskTextField!
  @IBOutlet weak var sendCodeButton: UIButton!
  @IBOutlet weak var timerLabel: UILabel!

  override func viewDidLoad() {
    super.viewDidLoad()
    configureTimerLabel()
    configureSendCodeButton()
  }

  private func configureCodeTextField() {
    codeTextField.layer.cornerRadius = Constant.textFieldCornerRadius
    codeTextField.layer.borderWidth = Constant.textFieldBorderWidth
    codeTextField.layer.borderColor = UIColor.black.cgColor
    codeTextField.addTarget(self, action: #selector(onCodeTextFieldChanged(_:)), for: .editingChanged)
  }

  private func configureTimerLabel() {
    var secondCount = 20
    Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true) { timer in
      secondCount -= 1
      self.timerLabel.text = "Можете запросить код повторно через: \(secondCount) секунд"
      if secondCount == 0 {
        self.timerLabel.text = ""
        timer.invalidate()
        self.enableSendCodeButton(isEnabled: true)
      }
    }
  }

  private func configureSendCodeButton() {
    sendCodeButton.layer.cornerRadius = Constant.buttonCornerRadius
    enableSendCodeButton(isEnabled: false)
  }

  private func enableSendCodeButton(isEnabled: Bool) {
    sendCodeButton.isEnabled = isEnabled
    sendCodeButton.backgroundColor = isEnabled ? .gray : .lightGray
  }

  @IBAction func sendCodeTapped(_ sender: UIButton) {
    fatalError("Not implemened yet")
  }

  @objc private func onCodeTextFieldChanged(_ sender: MaskTextField) {
    guard
      let count = sender.getText()?.count,
      count >= Constant.codeLength
    else {
        enableSendCodeButton(isEnabled: false)
        return
    }
    enableSendCodeButton(isEnabled: true)
  }
}
