//
//  SignUpViewController.swift
//  ios
//
//  Created by Артем Розанов on 17.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit
import shared

protocol SignUpView: UIViewController {
  func onRequestedComplete()
  func onRequestedError(_ error: Error)
}

final class SignUpViewController: UIViewController, SignUpView {

  var interactor: SignUpInteractor?

  private var nameIsNotEmty = false
  private var surnamtIsNotEmpty = false
  private var phoneIsFill = false

  private var nameLabel: UILabel
  private var nameTextField: UITextField
  private var surnameLabel: UILabel
  private var surnameTextField: UITextField
  private var phoneLabel: UILabel
  private var phoneTextField: PhoneTextField
  private var registerButton: UIButton
  private var codeTextField: UITextField
  private var timerLabel: UILabel

  init() {
    nameLabel = UILabel()
    surnameLabel = UILabel()
    phoneLabel = UILabel()
    nameTextField = UITextField()
    surnameTextField = UITextField()
    phoneTextField = PhoneTextField()
    registerButton = UIButton(type: .system)
    codeTextField = UITextField()
    timerLabel = UILabel()
    super.init(nibName: nil, bundle: nil)
  }

  required init?(coder: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }

  override func viewDidLoad() {
    super.viewDidLoad()
    view.backgroundColor = .white
    configureNavBar()
    configureViews()
    configureButton()
  }

  private func configureNavBar() {
    navigationController?.navigationBar.prefersLargeTitles = false
    navigationItem.title = "Регистрация"
  }

  private func configureViews() {
    nameTextField.translatesAutoresizingMaskIntoConstraints = false
    surnameTextField.translatesAutoresizingMaskIntoConstraints = false
    nameLabel.translatesAutoresizingMaskIntoConstraints = false
    surnameLabel.translatesAutoresizingMaskIntoConstraints = false
    phoneLabel.translatesAutoresizingMaskIntoConstraints = false
    view.addSubview(nameLabel)
    nameLabel.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 10).isActive = true
    nameLabel.heightAnchor.constraint(equalToConstant: 17).isActive = true
    nameLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    nameLabel.text = "Имя"
    view.addSubview(nameTextField)
    nameTextField.topAnchor.constraint(equalTo: nameLabel.bottomAnchor, constant: 5).isActive = true
    nameTextField.heightAnchor.constraint(equalToConstant: 40).isActive = true
    nameTextField.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    nameTextField.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
    nameTextField.addTarget(self, action: #selector(nameTextFieldFilled(_:)), for: .editingChanged)
    nameTextField.layer.cornerRadius = Constant.textFieldCornerRadius
    nameTextField.layer.borderWidth = Constant.textFieldBorderWidth
    nameTextField.layer.borderColor = UIColor.black.cgColor
    nameTextField.text = "  "
    view.addSubview(surnameLabel)
    surnameLabel.topAnchor.constraint(equalTo: nameTextField.bottomAnchor, constant: 10).isActive = true
    surnameLabel.heightAnchor.constraint(equalToConstant: 17).isActive = true
    surnameLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    surnameLabel.text = "Фамилия"
    view.addSubview(surnameTextField)
    surnameTextField.topAnchor.constraint(equalTo: surnameLabel.bottomAnchor, constant: 5).isActive = true
    surnameTextField.heightAnchor.constraint(equalToConstant: 40).isActive = true
    surnameTextField.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    surnameTextField.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
    surnameTextField.addTarget(self, action: #selector(surnameTextFieldFilled(_:)), for: .editingChanged)
    surnameTextField.layer.cornerRadius = Constant.textFieldCornerRadius
    surnameTextField.layer.borderWidth = Constant.textFieldBorderWidth
    surnameTextField.layer.borderColor = UIColor.black.cgColor
    surnameTextField.text = "  "
    view.addSubview(phoneLabel)
    phoneLabel.topAnchor.constraint(equalTo: surnameTextField.bottomAnchor, constant: 10).isActive = true
    phoneLabel.heightAnchor.constraint(equalToConstant: 17).isActive = true
    phoneLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    phoneLabel.text = "Телефон"
    view.addSubview(phoneTextField)
    phoneTextField.topAnchor.constraint(equalTo: phoneLabel.bottomAnchor, constant: 5).isActive = true
    phoneTextField.heightAnchor.constraint(equalToConstant: 40).isActive = true
    phoneTextField.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    phoneTextField.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
    phoneTextField.addTarget(self, action: #selector(phoneTextFieldFilled(_:)), for: .editingChanged)
  }

  private func configureButton() {
    view.addSubview(registerButton)
    registerButton.center = view.center
    registerButton.translatesAutoresizingMaskIntoConstraints = false
    registerButton.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true
    registerButton.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
    registerButton.widthAnchor.constraint(equalToConstant: 200).isActive = true
    registerButton.heightAnchor.constraint(equalToConstant: 50).isActive = true
    registerButton.backgroundColor = .lightGray
    registerButton.setTitle("Войти", for: .normal)
    registerButton.setTitleColor(.white, for: .normal)
    registerButton.addTarget(self, action: #selector(registerTapped(_:)), for: .touchUpInside)
    registerButton.isEnabled = false
    registerButton.layer.cornerRadius = 10
  }

  @objc private func registerTapped(_ sender: UIButton) {
    onRequestedComplete()
//    guard
//      let name = nameTextField.text,
//      let lastName = surnameTextField.text,
//      let phone = phoneTextField.text
//    else {
//      return
//    }
//    interactor?.signUpDidRequestVerificationCode(
//      self,
//      forSignUp: SignUp(
//      firstName: name, lastName: lastName, phone: phone)
//    )
//    enableButton(isEnabled: false)
  }

  @objc private func nameTextFieldFilled(_ sender: UITextField) {
    defer { enableButton(isEnabled: nameIsNotEmty && surnamtIsNotEmpty && phoneIsFill) }
    guard
      let count = sender.text?.count,
      count >= 3
    else {
      sender.text = "  "
      nameIsNotEmty = false
      return
    }
    nameIsNotEmty = true
  }

  @objc private func surnameTextFieldFilled(_ sender: UITextField) {
    defer { enableButton(isEnabled: nameIsNotEmty && surnamtIsNotEmpty && phoneIsFill) }
    guard
      let count = sender.text?.count,
      count >= 3
    else {
      sender.text = "  "
      surnamtIsNotEmpty = false
      return
    }
    surnamtIsNotEmpty = true
  }

  @objc private func phoneTextFieldFilled(_ sender: UITextField) {
    defer { enableButton(isEnabled: nameIsNotEmty && surnamtIsNotEmpty && phoneIsFill) }
    guard
      let count = sender.text?.count,
      count >= Constant.phoneLength
    else {
      phoneIsFill = false
      return
    }
    phoneIsFill = true
  }

  private func enableButton(isEnabled: Bool) {
    registerButton.isEnabled = isEnabled
    registerButton.backgroundColor = isEnabled ? .gray : .lightGray
  }

  private func removeInfoLabels() {
    nameTextField.removeFromSuperview()
    nameLabel.removeFromSuperview()
    surnameLabel.removeFromSuperview()
    surnameTextField.removeFromSuperview()
    phoneLabel.removeFromSuperview()
    phoneTextField.removeFromSuperview()
  }

  private func showCodeLabel() {
    view.addSubview(codeTextField)
    codeTextField.translatesAutoresizingMaskIntoConstraints = false
    codeTextField.bottomAnchor.constraint(equalTo: timerLabel.topAnchor, constant: -10).isActive = true
    codeTextField.heightAnchor.constraint(equalToConstant: 40).isActive = true
    codeTextField.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    codeTextField.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
    codeTextField.layer.cornerRadius = Constant.textFieldCornerRadius
    codeTextField.layer.borderWidth = Constant.textFieldBorderWidth
    codeTextField.layer.borderColor = UIColor.black.cgColor
  }

  private func showTimerLabel() {
    timerLabel.translatesAutoresizingMaskIntoConstraints = false
    view.addSubview(timerLabel)
    timerLabel.bottomAnchor.constraint(equalTo: registerButton.topAnchor, constant: -10).isActive = true
    timerLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    timerLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
    timerLabel.heightAnchor.constraint(equalToConstant: 50).isActive = true
    timerLabel.textAlignment = .center
    timerLabel.numberOfLines = 0
    timerLabel.textColor = .darkGray
    var secondCount = 20
    Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true) { timer in
      secondCount -= 1
      self.timerLabel.text = "Можете запросить код повторно через: \(secondCount) секунд"
      if secondCount == 0 {
        timer.invalidate()
        self.enableButton(isEnabled: true)
      }
    }
  }

  func onRequestedComplete() {
    enableButton(isEnabled: false)
    removeInfoLabels()
    showTimerLabel()
    showCodeLabel()
  }

  func onRequestedError(_ error: Error) {
    fatalError("init(coder:) has not been implemented")
  }
}
