//
//  SignInInteractor.swift
//  ios
//
//  Created by Артем Розанов on 09.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

protocol SignInInteractor {
  func signInSceneDidRequestCode(_ signInView: SignInView)
}

final class SignInInteractorImpl: SignInInteractor {

  func signInSceneDidRequestCode(_ signInView: SignInView) {

  }
}
