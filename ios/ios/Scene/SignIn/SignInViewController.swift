//
//  SignInViewController.swift
//  ios
//
//  Created by Артем Розанов on 03.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

protocol SignInView: UIViewController {
  func onCodeRequestCompleted()
}

final class SignInViewController: UIViewController, SignInView {

  // MARK: - properties

  var router: SignInRouter?
  var interactor: SignInInteractor?

  private var phoneTextField: MaskTextField
  private var registerButton: UIButton
  private var toSignUpButton: UIButton

  private var context: UIViewController?

  // MARK: - Lyfecycle

  init() {
    phoneTextField = MaskTextField(formattingPattern: "*** ***-**-**", prefix: " +7 ")
    registerButton = UIButton(type: .system)
    toSignUpButton = UIButton(type: .system)
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
    configureToSignUpButton()
    configureRegisterButton()
  }

  // MARK: - Configure UI

  func configureNavBar() {
    navigationItem.largeTitleDisplayMode = .never
    navigationController?.navigationBar.prefersLargeTitles = true
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

  private func configureRegisterButton() {
    view.addSubview(registerButton)
    registerButton.center = view.center
    registerButton.translatesAutoresizingMaskIntoConstraints = false
    registerButton.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true
    registerButton.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
    registerButton.widthAnchor.constraint(equalToConstant: 200).isActive = true
    registerButton.heightAnchor.constraint(equalToConstant: 50).isActive = true
    registerButton.setTitle("Войти", for: .normal)
    registerButton.setTitleColor(.white, for: .normal)
    registerButton.addTarget(self, action: #selector(registerTapped(_:)), for: .touchUpInside)
    registerButton.layer.cornerRadius = 10
    registerButton.enableButton(isEnabled: false)
  }

  private func configureToSignUpButton() {
    view.addSubview(toSignUpButton)
    toSignUpButton.translatesAutoresizingMaskIntoConstraints = false
    toSignUpButton.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    toSignUpButton.topAnchor.constraint(equalTo: phoneTextField.bottomAnchor, constant: 10).isActive = true
    toSignUpButton.widthAnchor.constraint(equalToConstant: 200).isActive = true
    toSignUpButton.heightAnchor.constraint(equalToConstant: 30).isActive = true
    toSignUpButton.setTitle("Ещё не зарегистрированы?", for: .normal)
    toSignUpButton.setTitleColor(.gray, for: .normal)
    toSignUpButton.addTarget(self, action: #selector(toSignUpTapped(_:)), for: .touchUpInside)
  }

  // MARK: - Actions

  @objc private func toSignUpTapped(_ sender: UIButton) {
    guard let context = context else { return }
    router?.signInShouldOpenSignUpScene(self, pass: context)
  }

  @objc private func registerTapped(_ sender: UIButton) {
    guard let phone = phoneTextField.getText() else { return }
    interactor?.signInSceneDidRequestCode(self, for: phone)
  }

  @objc private func textFieldFilled(_ sender: UITextField) {
    guard
      let count = sender.text?.count,
      count >= 17
    else {
      registerButton.enableButton(isEnabled: false)
      return
    }
    registerButton.enableButton(isEnabled: true)
  }

  // MARK: - SignInView methods

  func onCodeRequestCompleted() {
    guard
      let phone = phoneTextField.getText(),
      let context = context
    else { return }
    router?.signInShouldOpenVerificationCodeScene(self, pass: phone, and: context)
  }
}

// MARK: - ContextDataDelegate

extension SignInViewController: ContextDataDelegate {
  func passedContext(_ context: UIViewController) {
    self.context = context
  }
}
