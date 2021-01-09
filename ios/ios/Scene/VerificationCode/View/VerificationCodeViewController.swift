//
//  EnterCodeViewController.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

protocol VerificationCodeView: UIViewController {
  func onStartSessionCompleted()
}

final class VerificationCodeViewController: UIViewController, VerificationCodeView {

  // MARK: - Class members

  static let storyboardName = "VerificationCodeViewController"
  static func storyboardInstance() -> Self? {
    let storyboard = UIStoryboard(name: storyboardName, bundle: nil)
    return storyboard.instantiateInitialViewController() as? Self
  }

  // MARK: - properties

  var interactor: VerificationCodeInteractor?
  var router: VerificationCodeRouter?

  @IBOutlet weak var codeTextField: MaskTextField!
  @IBOutlet weak var sendCodeButton: UIButton!
  @IBOutlet weak var timerLabel: UILabel!

  private var phone = ""
  private var context: UIViewController?

  // MARK: - Lyfecycle

  override func viewDidLoad() {
    super.viewDidLoad()
    configureTimerLabel()
    configureSendCodeButton()
  }

  // MARK: - Configure UI

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

  // MARK: - Actions

  @IBAction func sendCodeTapped(_ sender: UIButton) {
    guard let verificationCode = codeTextField.getText() else { return }
    interactor?.verificationCodeStartedSession(self, for: phone, and: verificationCode)
  }

  @objc private func onCodeTextFieldChanged(_ sender: MaskTextField) {
    guard sender.getText()?.count ?? 0 >= Constant.codeLength else {
      enableSendCodeButton(isEnabled: false)
      return
    }
    enableSendCodeButton(isEnabled: true)
  }

  // MARK: - VerificationCodeView

  func onStartSessionCompleted() {
    guard let context = context else { return }
    router?.verificationCodeShouldBackTo(self, contex: context)
  }
}

// MARK: - SignInViewDataDelegate

extension VerificationCodeViewController: SignInViewDataDelegate {
  func signInViewPassed(_ data: String) {
    phone = data
  }
}

// MARK: - ContextDataDelegate

extension VerificationCodeViewController: ContextDataDelegate {
  func passedContext(_ context: UIViewController) {
    self.context = context
  }
}
