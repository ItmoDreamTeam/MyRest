//
//  UserInfoInteractor.swift
//  ios
//
//  Created by Артем Розанов on 27.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import shared

protocol UserInfoInteractor {
  func userInfoShouldLogOut(_ userInfoView: UserInfoView)
}

final class UserInfoInteractorImpl: UserInfoInteractor {

  private let accessTokenProvider: AccessTokenProviderImpl

  init(accessTokenProvider: AccessTokenProviderImpl) {
    self.accessTokenProvider = accessTokenProvider
  }

  func userInfoShouldLogOut(_ userInfoView: UserInfoView) {
    accessTokenProvider.removeToken()
  }
}
