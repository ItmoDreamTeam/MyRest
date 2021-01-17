//
//  BookRouter.swift
//  ios
//
//  Created by Артем Розанов on 18.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

protocol BookRouter {
  func bookShouldDismiss(_ bookView: BookView)
}

final class BookRouterImpl: BookRouter {
  func bookShouldDismiss(_ bookView: BookView) {
    bookView.dismiss(animated: true, completion: nil)
  }
}
