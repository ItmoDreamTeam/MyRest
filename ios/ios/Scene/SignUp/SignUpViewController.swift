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
  func onVerificationCodeRequestCompleted()
}

final class SignUpViewController: UIViewController, SignUpView {

  // MARK: - properties

  var interactor: SignUpInteractor?
  var router: SignUpRouter?

  private var nameIsNotEmpty = false
  private var surnameIsNotEmpty = false
  private var phoneIsFilled = false
  private var context: UIViewController?

  private var nameLabel: UILabel
  private var nameTextField: UITextField
  private var surnameLabel: UILabel
  private var surnameTextField: UITextField
  private var phoneLabel: UILabel
  private var phoneTextField: MaskTextField
  private var getCodeButton: UIButton

  // MARK: - lifecycle

  init() {
    nameLabel = UILabel()
    nameTextField = UITextField()
    surnameLabel = UILabel()
    surnameTextField = UITextField()
    phoneLabel = UILabel()
    phoneTextField = MaskTextField(formattingPattern: "*** ***-**-**", prefix: " +7 ")
    getCodeButton = UIButton(type: .system)
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
    configureGetCodeButton()
  }

  // MARK: - private layout views
  
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

  private func configureGetCodeButton() {
    view.addSubview(getCodeButton)
    getCodeButton.translatesAutoresizingMaskIntoConstraints = false
    getCodeButton.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true
    getCodeButton.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
    getCodeButton.widthAnchor.constraint(equalToConstant: 200).isActive = true
    getCodeButton.heightAnchor.constraint(equalToConstant: 50).isActive = true
    getCodeButton.setTitle("Получить код", for: .normal)
    getCodeButton.setTitleColor(.white, for: .normal)
    getCodeButton.addTarget(self, action: #selector(getCodeTapped(_:)), for: .touchUpInside)
    getCodeButton.layer.cornerRadius = 10
    getCodeButton.enableButton(isEnabled: false)
  }

  // MARK: - private textField methods

  @objc private func phoneTextFieldFilled(_ sender: MaskTextField) {
    defer {
      getCodeButton.enableButton(isEnabled: nameIsNotEmpty && surnameIsNotEmpty && phoneIsFilled)
    }
    guard sender.getText()?.count ?? 0 >= Constant.phoneLength else {
      phoneIsFilled = false
      return
    }
    phoneIsFilled = true
  }

  @objc private func nameTextFieldFilled(_ sender: UITextField) {
    textFieldFilled(sender)
  }

  @objc private func surnameTextFieldFilled(_ sender: UITextField) {
    textFieldFilled(sender)
  }

  private func textFieldFilled(_ sender: UITextField) {
    defer {
      getCodeButton.enableButton(isEnabled: nameIsNotEmpty && surnameIsNotEmpty && phoneIsFilled)
    }
    if sender.text?.count ?? 0 >= 3 {
      sender.text = "  "
      updateIsNotEmpty(sender: sender, value: true)
    } else {
      updateIsNotEmpty(sender: sender, value: false)
    }
  }

  private func updateIsNotEmpty(sender: UITextField, value: Bool) {
    if sender.hashValue == nameTextField.hashValue {
      nameIsNotEmpty = value
    } else {
      surnameIsNotEmpty = value
    }
  }

  // MARK: - private buttons methods
  @objc private func getCodeTapped(_ sender: UIButton) {
    guard
      let name = nameTextField.text,
      let lastName = surnameTextField.text,
      let phone = phoneTextField.getText()
    else { return }
    interactor?.signUpDidRequestVerificationCode(
      self,
      forSignUp: SignUp(firstName: name, lastName: lastName, phone: phone)
    )
    getCodeButton.enableButton(isEnabled: false)
  }

  // MARK: - SignUpView methods

  func onVerificationCodeRequestCompleted() {
    guard
      let phone = phoneTextField.getText(),
      let context = context
    else { return }
    router?.signUpShouldOpenVerificationCodeScene(self, pass: phone, and: context)
  }
}

// MARK: - ContextDataDelegate

extension SignUpViewController: ContextDataDelegate {
  func passedContext(_ context: UIViewController) {
    self.context = context
  }
}
