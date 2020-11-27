import UIKit
import Dip

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
  var window: UIWindow?
  private let dependencyContainer = DependencyContainer(autoInjectProperties: false)

  func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    window = UIWindow()
    window?.makeKeyAndVisible()

    let appDependencies = AppDependencies(dependencyContainer: dependencyContainer)
    let rootVC = appDependencies.getRootViewController()
    let navigationController = UINavigationController(rootViewController: rootVC)
    window?.rootViewController = navigationController

    return true
  }
}
