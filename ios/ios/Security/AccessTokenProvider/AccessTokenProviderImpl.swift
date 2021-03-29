//
//  AccessTokenProviderImpl.swift
//  ios
//
//  Created by Артем Розанов on 23.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftKeychainWrapper

final class AccessTokenProviderImpl: AccessTokenProvider {

  private let keychainWrapper: KeychainWrapper

  init(keychainWrapper: KeychainWrapper) {
    self.keychainWrapper = keychainWrapper
  }

  var accessToken: String? {
    get { keychainWrapper.string(forKey: "token") }
    set {
      if let value = newValue {
        keychainWrapper.set(value, forKey: "token")
      }
    }
  }
}
