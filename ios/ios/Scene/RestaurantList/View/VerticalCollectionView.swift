//
//  VerticalCollectionView.swift
//  ios
//
//  Created by Артем Розанов on 27.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

protocol VerticalCollectionViewDelegate {
  func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath)
}

final class VerticalCollectionView: UICollectionView, ConfigurableView {
  typealias Model = [RestaurantListViewModel]

  private let sectionInsets = UIEdgeInsets(top: 10, left: 10, bottom: 10, right: 10)
  private let minimumItemSpacing: CGFloat = 3
  private let itemsInRow: CGFloat = 2

  private var viewModel: [RestaurantListViewModel] = []
  var fotterView: LoadingFotter?
  var verticalCollectionViewDelegate: VerticalCollectionViewDelegate?

  init() {
    let layout = UICollectionViewFlowLayout()
    layout.scrollDirection = .vertical
    super.init(frame: .zero, collectionViewLayout: layout)

    delegate = self
    dataSource = self

    translatesAutoresizingMaskIntoConstraints = false
    register(UINib(nibName: RestaurantCell.reuseId, bundle: nil), forCellWithReuseIdentifier: RestaurantCell.reuseId)
    backgroundColor = .white
    register(
      LoadingFotter.self,
      forSupplementaryViewOfKind: UICollectionView.elementKindSectionFooter,
      withReuseIdentifier: LoadingFotter.reuseId
    )
    contentInset = .zero
  }

  required init?(coder: NSCoder) {
    return nil
  }

  func configure(with model: [RestaurantListViewModel]) {
    self.viewModel = model
  }
}

// MARK: - UICollectionViewDataSource methods
extension VerticalCollectionView: UICollectionViewDataSource {

  func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
    return viewModel.count
  }

  func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
    guard let cell = dequeueReusableCell(
      withReuseIdentifier: RestaurantCell.reuseId,
      for: indexPath) as? RestaurantCell else {
      return UICollectionViewCell()
    }
    let restaurant = viewModel[indexPath.row]
    cell.configure(with: restaurant)
    return cell
  }

  func collectionView(
    _ collectionView: UICollectionView,
    viewForSupplementaryElementOfKind kind: String,
    at indexPath: IndexPath
  ) -> UICollectionReusableView {
    guard let fotter = collectionView.dequeueReusableSupplementaryView(
      ofKind: UICollectionView.elementKindSectionFooter,
      withReuseIdentifier: LoadingFotter.reuseId,
      for: indexPath) as? LoadingFotter
      else { fatalError("Wrong fotter reuse id") }
    fotterView = fotter
    return fotter
  }
}

extension VerticalCollectionView: UICollectionViewDelegate {
  func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
    verticalCollectionViewDelegate?.collectionView(collectionView, didSelectItemAt: indexPath)
  }
}

// MARK: - UICollectionViewDelegateFlowLayout methods
extension VerticalCollectionView: UICollectionViewDelegateFlowLayout {

  func collectionView(
    _ collectionView: UICollectionView,
    layout collectionViewLayout: UICollectionViewLayout,
    sizeForItemAt indexPath: IndexPath
  ) -> CGSize {
    let paddingSpace = sectionInsets.left + sectionInsets.right + minimumItemSpacing * (itemsInRow - 1)
    let availableWidth = collectionView.bounds.width - paddingSpace
    let widthPerItem = availableWidth / itemsInRow
    return CGSize(width: widthPerItem, height: widthPerItem)
  }

  func collectionView(
    _ collectionView: UICollectionView,
    layout collectionViewLayout: UICollectionViewLayout,
    insetForSectionAt section: Int
  ) -> UIEdgeInsets {
    return sectionInsets
  }

  func collectionView(
    _ collectionView: UICollectionView,
    layout collectionViewLayout: UICollectionViewLayout,
    minimumLineSpacingForSectionAt section: Int
  ) -> CGFloat {
    return minimumItemSpacing
  }

  func collectionView(
    _ collectionView: UICollectionView,
    layout collectionViewLayout: UICollectionViewLayout,
    minimumInteritemSpacingForSectionAt section: Int
  ) -> CGFloat {
    return minimumItemSpacing
  }

  func collectionView(
    _ collectionView: UICollectionView,
    layout collectionViewLayout: UICollectionViewLayout,
    referenceSizeForFooterInSection section: Int
  ) -> CGSize {
    return CGSize(width: collectionView.bounds.width, height: 100)
  }
}
