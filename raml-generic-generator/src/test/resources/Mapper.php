<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Base;

interface Mapper
{
    public function mapData($class, $data);

    public function resolveDiscriminator($class, $discriminator, array $subTypes, array $data);
}
