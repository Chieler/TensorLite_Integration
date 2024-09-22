# import json
# import tensorflow as tf
# import keras_cv
# # prints out tensorflow version
# # converts model_weights.h5 to .tflite file type by first loading the configuration
# # then we create the backbone
# #next we reconstruct the model and load it with weights
#
# print(tf.__version__)
# configPath = "/Path to config.json"
# weightsPath = "/Path to model.weights.h5"
# def loadEfficientV2_backbone(config):
#     return keras_vc.models.loadEfficientNetV2Backbone(**config)
#
# def reconstruct_model(configPath, weightsPath):
#     # opens the config.json file and reads assigns it to config_file
#     with open (configPath, "r") as config_file:
#         # loads the insides of the file to config variable
#         config = json.load(config_file)
#         # config file has a nested dictionary lawout, and we are getting the config file for the backbone here
#         backbone_config = config['config']['backbone']['config']
#         #load the backbones here
#         backbone = load_efficientnetv2_backbone(backbone_config)
#         # defines model structure according to the config.json file structure
#         model = keras_cv.models.ImageClassifier(
#             backbone=backbone,
#             num_classes=config['config']['num_classes'],
#             pooling=config['config']['pooling'],
#             activation=config['config']['activation']
#         )
#     # load weights
#     model.load_weights(weights_path)
#     return model
#
# # assigns the model (currently tensorflow type) to model var
# model = reconstruct_model(configPath, weightsPath)
# # the converter tf.lite.tfLiteConverter.from_keras_model() method on model
# converter = tf.lite.TFLiteConverter.from_keras_model(model)
# tfLiteModel = converter.convert()
# # now that the model is converted to tfLite form, we write it as a file, wb stands for wrtie in binary
# with open("efficientnet_model.tflite", 'wb') as file:
#     file.write(tfLiteModel)
#
# #key values of config.json
# #First few key-value pairs:
# #module: keras_cv.src.models.classification.image_classifier
# #class_name: ImageClassifier
# #config: {'name': 'image_classifier', 'trainable': True, 'backbone':
# #{'module': 'keras_cv.src.models.backbones.efficientnet_v2.efficientnet_v2_backbone',
# #'class_name': 'EfficientNetV2Backbone', 'config': {'name':
# # 'efficient_net_v2_backbone_2', 'trainable': True, 'include_rescaling': True,
# #'width_coefficient': 1.0, 'depth_coefficient': 1.0, 'skip_connection_dropout':
# # 0.2, 'depth_divisor': 8, 'min_depth': 8, 'activation': 'swish', 'input_shape':
# #[None, None, 3], 'input_tensor': None, 'stackwise_kernel_sizes': [3, 3, 3, 3, 3, 3],
# #'stackwise_num_repeats': [2, 4, 4, 6, 9, 15], 'stackwise_input_filters':
# # [24, 24, 48, 64, 128, 160], 'stackwise_output_filters': [24, 48, 64, 128, 160,
# # 256], 'stackwise_expansion_ratios': [1, 4, 4, 4, 6, 6],
# # 'stackwise_squeeze_and_excite_ratios': [0.0, 0.0, 0, 0.25, 0.25, 0.25],
# #'stackwise_strides': [1, 2, 2, 2, 1, 2], 'stackwise_conv_types': ['fused',
# #'fused', 'fused', 'unfused', 'unfused', 'unfused']}, 'registered_name': 'keras_cv>EfficientNetV2Backbone'},
# # 'num_classes': 1000, 'pooling': 'avg', 'activation': 'softmax'}
# #registered_name: keras_cv>ImageClassifier
# #weights: model.weights.h5