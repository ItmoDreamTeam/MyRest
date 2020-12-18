//
//  SignUpViewController.swift
//  ios
//
//  Created by Артем Розанов on 17.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit
import SwiftMaskTextfield

protocol SignUpView: UIViewController {}

final class SignUpViewController: UIViewController, SignUpView {

  private var nameTextField: SwiftMaskTextfield
  private var surnameTextField: SwiftMaskTextfield
  private var phoneTextField: SwiftMaskTextfield
  private var registerButton: UIButton

  init() {
    nameTextField = SwiftMaskTextfield()
    surnameTextField = SwiftMaskTextfield()
    phoneTextField = SwiftMaskTextfield()
    registerButton = UIButton(type: .system)
    super.init(nibName: nil, bundle: nil)
  }

  required init?(coder: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }

  override func viewDidLoad() {
    super.viewDidLoad()
    view.backgroundColor = .white
    configureTextFields()
  }

  private func configureTextFields() {
    nameTextField.translatesAutoresizingMaskIntoConstraints = false
    surnameTextField.translatesAutoresizingMaskIntoConstraints = false
    view.addSubview(nameTextField)
    nameTextField.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 40).isActive = true
    nameTextField.heightAnchor.constraint(equalToConstant: 60).isActive = true
    nameTextField.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    nameTextField.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
    nameTextField.layer.cornerRadius = Constant.textFieldCornerRadius
    nameTextField.layer.borderWidth = Constant.textFieldBorderWidth
    nameTextField.layer.borderColor = UIColor.black.cgColor
    nameTextField.prefix = " "
    view.addSubview(surnameTextField)
    surnameTextField.topAnchor.constraint(equalTo: nameTextField.bottomAnchor, constant: 40).isActive = true
    surnameTextField.heightAnchor.constraint(equalToConstant: 60).isActive = true
    surnameTextField.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    surnameTextField.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
    surnameTextField.layer.cornerRadius = Constant.textFieldCornerRadius
    surnameTextField.layer.cornerRadius = Constant.textFieldCornerRadius
    surnameTextField.layer.borderWidth = Constant.textFieldBorderWidth
    surnameTextField.layer.borderColor = UIColor.black.cgColor
    surnameTextField.prefix = " "
    view.addSubview(phoneTextField)
    phoneTextField.topAnchor.constraint(equalTo: surnameTextField.bottomAnchor, constant: 40).isActive = true
    phoneTextField.heightAnchor.constraint(equalToConstant: 60).isActive = true
    phoneTextField.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    phoneTextField.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
    phoneTextField.addTarget(self, action: #selector(textFieldFilled(_:)), for: .editingChanged)
    phoneTextField.layer.cornerRadius = Constant.textFieldCornerRadius
    phoneTextField.layer.borderWidth = Constant.textFieldBorderWidth
    phoneTextField.layer.borderColor = UIColor.black.cgColor
    phoneTextField.prefix = " +7 "
    phoneTextField.formatPattern = "### ###-##-##"
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
    fatalError("Not implemented yet")
  }

  @objc private func textFieldFilled(_ sender: UITextField) {
    guard
      let count = sender.text?.count,
      count >= 17
    else {
      registerButton.isEnabled = false
      registerButton.backgroundColor = .lightGray
      return
    }
    registerButton.isEnabled = true
    registerButton.backgroundColor = .gray
  }
}
