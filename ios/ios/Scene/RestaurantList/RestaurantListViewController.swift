//
//  RestaurantListViewController.swift
//  ios
//
//  Created by Артем Розанов on 27.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit
import shared

protocol RestaurantListView: UIViewController {
  func onRestaurantsFetchCompleted(_ restaurants: [RestaurantInfo])
  func onRestaurantsFetchError(_ error: Error)
  func onUserFetchCompleted(_ user: Profile)
}

final class RestaurantListViewController: UIViewController, RestaurantListView {

  // MARK: - properties

  private var searchView: SearchView
  private var collectionView: VerticalCollectionView

  var router: RestaurantListRouter?
  var interactor: RestaurantListInteractor?

  // MARK: - Lyfecycle

  init() {
    searchView = SearchView()
    collectionView = VerticalCollectionView()
    super.init(nibName: nil, bundle: nil)
  }

  required init?(coder: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }

  override func viewDidLoad() {
    super.viewDidLoad()
    view.backgroundColor = .white
    interactor?.restaurantListDidRequestRestaurants(self, byKeyword: "")
    configureNavBar()
    configureSearchTextField()
    configureCollectionView()
  }

  // MARK: - UI Layout

  private func configureNavBar() {
    navigationItem.title = "MyRest"
    navigationItem.largeTitleDisplayMode = .always
    navigationController?.navigationBar.prefersLargeTitles = true

    navigationItem.rightBarButtonItem = UIBarButtonItem(
      image: UIImage(named: "info"),
      style: .plain,
      target: self,
      action: #selector(goToAboutScene)
    )
    navigationItem.rightBarButtonItem?.tintColor = .black
  }

  private func configureSearchTextField() {
    view.addSubview(searchView)
    searchView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor).isActive = true
    searchView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -5).isActive = true
    searchView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 5).isActive = true
  }

  private func configureCollectionView() {
    view.addSubview(collectionView)
    collectionView.verticalCollectionViewDelegate = self
    collectionView.topAnchor.constraint(equalTo: searchView.bottomAnchor).isActive = true
    collectionView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
    collectionView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
    collectionView.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
  }

  // MARK: - Actions

  @objc private func goToAboutScene() {
    interactor?.restaurantListDidRequestUserInfo(self)
  }

  // MARK: - RestaurantListView methods

  func onRestaurantsFetchCompleted(_ restaurants: [RestaurantInfo]) {
    collectionView.configure(with: restaurants)
    collectionView.fotterView?.activityIndicatorView.stopAnimating()
    DispatchQueue.main.async {
      self.collectionView.reloadData()
    }
  }

  func onRestaurantsFetchError(_ error: Error) {
    fatalError("Not implemented yet")
  }

  func onUserFetchCompleted(_ user: Profile) {
    router?.restaurantListShouldOpenUserInfoScene(self, pass: user)
  }
}

extension RestaurantListViewController: VerticalCollectionViewDelegate {
  func collectionView(_ collectionView: UICollectionView, didSelectItem item: RestaurantInfo) {
    fatalError("Not implemented yet")
  }
}
