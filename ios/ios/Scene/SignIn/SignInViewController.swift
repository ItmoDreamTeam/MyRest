//
//  SignInViewController.swift
//  ios
//
//  Created by Артем Розанов on 03.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

protocol SignInView: UIViewController {}

final class SignInViewController: UIViewController, SignInView {

  private var phoneTextField: MaskTextField
  private var registerButton: UIButton

  init() {
    phoneTextField = MaskTextField(formattingPattern: "*** ***-**-** ", prefix: " +7 ")
    registerButton = UIButton(type: .system)
    super.init(nibName: nil, bundle: nil)
  }

  required init?(coder: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }

  override func viewDidLoad() {
    super.viewDidLoad()
    view.backgroundColor = .white
    configureNavBar()
    configurePhoneTextField()
    configureButton()
  }

  private func configureNavBar() {
    navigationController?.navigationBar.prefersLargeTitles = false
    navigationItem.title = "Вход"
  }

  private func configurePhoneTextField() {
    view.addSubview(phoneTextField)
    phoneTextField.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 40).isActive = true
    phoneTextField.heightAnchor.constraint(equalToConstant: 60).isActive = true
    phoneTextField.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    phoneTextField.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
    phoneTextField.addTarget(self, action: #selector(textFieldFilled(_:)), for: .editingChanged)
    phoneTextField.becomeFirstResponder()
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
    // MARK: - not implementer yet
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
