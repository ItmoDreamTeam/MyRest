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

  private var phoneTextField: PhoneTextField
  private var registerButton: UIButton

  init() {
    phoneTextField = PhoneTextField()
    registerButton = UIButton()
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
  }

  private func configureNavBar() {
    navigationController?.navigationBar.prefersLargeTitles = false
    navigationItem.title = "Enter"
  }

  private func configurePhoneTextField() {
    view.addSubview(phoneTextField)
    phoneTextField.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 40).isActive = true
    phoneTextField.heightAnchor.constraint(equalToConstant: 60).isActive = true
    phoneTextField.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    phoneTextField.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
  }

  private func addButton() {

  }
}
