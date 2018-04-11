<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Exception\InvalidArgumentException;
use Test\Types\AnimalModel;

class CatModel extends AnimalModel implements Cat {
    const DISCRIMINATOR_VALUE = 'cat';

}
