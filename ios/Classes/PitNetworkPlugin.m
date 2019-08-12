#import "PitNetworkPlugin.h"
#import <pit_network/pit_network-Swift.h>

@implementation PitNetworkPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftPitNetworkPlugin registerWithRegistrar:registrar];
}
@end
