//
//  UserInfoRouter.swift
//  ios
//
//  Created by Артем Розанов on 24.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

protocol UserInfoRouter {
  func userInfoShouldOpenUsersBookScene(_ userInfoScene: UserInfoView)
}

final class UserInfoRouterImpl: UserInfoRouter {

  private let usersBookScene: UsersBookView

  init(usersBookScene: UsersBookView) {
    self.usersBookScene = usersBookScene
  }

  func userInfoShouldOpenUsersBookScene(_ userInfoScene: UserInfoView) {
    userInfoScene.navigationController?.pushViewController(usersBookScene, animated: true)
  }
}
